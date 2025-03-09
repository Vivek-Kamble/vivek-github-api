package vivek.github.api.controller;

import vivek.github.api.dto.CreateIssueRequest;
import vivek.github.api.feignclient.GitHubFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/github")
public class GitHubController {

    @Autowired
    GitHubFeignClient gitHubFeignClient;

    @GetMapping
    public Map<String, Object> getGithubData(
            @RequestParam String username,
            @RequestHeader("Authorization") String authHeader // ✅ Token taken from the request header
    ) {
        if (authHeader == null || authHeader.isEmpty()) {
            throw new IllegalArgumentException("Authorization header is required");
        }

        // Fetch user details
        Map<String, Object> userDetails = gitHubFeignClient.getUserDetails(username, authHeader);

        // Fetch repositories
        List<Map<String, Object>> repositories = gitHubFeignClient.getUserRepositories(username, authHeader);

        // Combine data into a single response
        Map<String, Object> response = new HashMap<>();
        response.put("username", userDetails.get("login"));
        response.put("followers", userDetails.get("followers"));
        response.put("following", userDetails.get("following"));
        response.put("repositories", repositories);
        response.put("repositories_count", repositories.size());

        return response;
    }


    @GetMapping("/{repoName}")
    public Map<String, Object> getRepoDetails(
            @RequestParam String username,  // Get username as query param
            @PathVariable String repoName,  // Get repo name from URL
            @RequestHeader("Authorization") String authHeader
    ) {
        if (authHeader == null || authHeader.isEmpty()) {
            throw new IllegalArgumentException("Authorization header is required");
        }

        return gitHubFeignClient.getRepoDetails(username, repoName, authHeader);
    }

    @PostMapping("/{repoName}/issues")
    public Map<String, Object> createIssue(
            @RequestParam String username, // ✅ Username in query param
            @PathVariable String repoName, // ✅ Repo name in URL
            @RequestHeader("Authorization") String authHeader, // ✅ Token from header
            @RequestBody CreateIssueRequest issueRequest // ✅ Request body with title & body
    ) {
        if (authHeader == null || authHeader.isEmpty()) {
            throw new IllegalArgumentException("Authorization header is required");
        }

        // Call Feign Client to create the issue
        Map<String, Object> issueResponse = gitHubFeignClient.createIssue(username, repoName, authHeader, issueRequest);

        Map<String, Object> response = new HashMap<>();
        response.put("issue_url", issueResponse.get("html_url"));
        return response;
    }

}

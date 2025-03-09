package vivek.github.api.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import vivek.github.api.dto.CreateIssueRequest;

import java.util.List;
import java.util.Map;

@FeignClient(name = "github", url = "https://api.github.com")
public interface GitHubFeignClient {

    @GetMapping("/users/{username}")
    Map<String, Object> getUserDetails(@PathVariable("username") String username,
                                       @RequestHeader("Authorization") String authHeader);

    @GetMapping("/users/{username}/repos")
    List<Map<String, Object>> getUserRepositories(@PathVariable("username") String username,
                                                  @RequestHeader("Authorization") String authHeader);

    @GetMapping("/repos/{username}/{repoName}")
    Map<String, Object> getRepoDetails(
            @PathVariable("username") String username,
            @PathVariable("repoName") String repoName,
            @RequestHeader("Authorization") String authHeader
    );

    @PostMapping("/repos/{username}/{repoName}/issues")
    Map<String, Object> createIssue(
            @PathVariable("username") String username,
            @PathVariable("repoName") String repoName,
            @RequestHeader("Authorization") String authHeader,
            @RequestBody CreateIssueRequest issueRequest // ✅ Issue data
    );
}

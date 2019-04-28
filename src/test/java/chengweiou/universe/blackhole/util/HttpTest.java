package chengweiou.universe.blackhole.util;


import org.junit.jupiter.api.Test;

public class HttpTest {

    @Test
    public void sync() {
//        HttpClient client = HttpClient.newBuilder()
//                .version(HttpClient.Version.HTTP_1_1).followRedirects(HttpClient.Redirect.NORMAL).connectTimeout(Duration.ofSeconds(20))
//                .proxy(ProxySelector.getDefault()).authenticator(Authenticator.getDefault())
//                .build();
//        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
//        response.statusCode();
//        response.body();
    }

    @Test
    public void async() {
//        HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://foo.com")).timeout(Duration.ofMinutes(2))
//                .header("", "").method("post", HttpRequest.BodyPublishers.)
//                .POST(HttpRequest.BodyPublishers.ofFile(Paths.get("file.json")))
//                .build();
//        HttpClient client = HttpClient.newBuilder().build();
//        client.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(HttpResponse::body).thenAccept(System.out::println);
    }
}

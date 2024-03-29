package hello.core.lifecycle;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    // 서비스 시작시 호출
    public void connect() {
        System.out.println("connect: " + url);
    }

    public void call(String message) {
        System.out.println("call: " + url + " message = " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close: " + url);
    }

    // 스프링에서 권장되는 어노테이션: @PostConstruct, @PreDestroy -> 자바에서 지원해주는 기능
    // 그러나 외부 라이브러리에서는 사용 불가능하다는 단점이 있다. -> @Bean의 initMethod, destroyMethod 사용
    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        disconnect();
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        connect();
        call("초기화 연결 메시지");
    }
}

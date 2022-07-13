package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//시도1. 인터페이스 InitializingBean, DisposableBean
//public class NetworkClient implements InitializingBean, DisposableBean {

//시도2. 빈 등록 초기화, 소멸 메서드 지정

//시도3. 애노테이션 @PostConstruct, @PreDestroy -> 최근 스프링에서 가장 권장하는 방법. 이걸로 사용하면 된다.
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("1. 생성자 호출, url = " + url);
    }

    public void setUrl(String url) {
        this.url = url;
    }

    //서비스 시작시 호출
    public void connect() {
        System.out.println("2. connect: " + url);
    }
    public void call(String message) {
        System.out.println("3. call: " + url + " message = " + message);
    }

    //서비스 종료시 호출
    public void disconnect() {
        System.out.println("4. close: " + url);
    }

    //시도1.
//    @Override //InitializingBean : 빈 초기화
//    //afterPropertiesSet : "의존관계 주입이 끝나면"
//    public void afterPropertiesSet() throws Exception {
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    @Override //DisposableBean : 빈 종료
//    public void destroy() throws Exception {
//        disconnect();
//    }

    //시도2. 빈 등록 초기화, 소멸 메서드 지정
//    public void init() {
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    public void close() {
//        disconnect();
//    }

    //시도3. 애노테이션 @PostConstruct, @PreDestroy
    @PostConstruct
    public void init() {
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        disconnect();
    }
}

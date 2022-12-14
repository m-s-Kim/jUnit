package site.metacoding.junitproject.util;

import org.springframework.stereotype.Component;

@Component // IoC컨테이너 등록
public class MailSenderStub implements MailSender {

    @Override
    public boolean send() {
        // TODO Auto-generated method stub
        return true;
    }

}

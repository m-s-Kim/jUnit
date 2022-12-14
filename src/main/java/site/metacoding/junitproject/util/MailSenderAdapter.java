package site.metacoding.junitproject.util;

public class MailSenderAdapter implements MailSender {

    private Mail mail;

    public MailSenderAdapter() {
        this.mail = new Mail();
    }

    @Override
    public boolean send() {
        // TODO Auto-generated method stub
        return mail.sendMail();
    }
}

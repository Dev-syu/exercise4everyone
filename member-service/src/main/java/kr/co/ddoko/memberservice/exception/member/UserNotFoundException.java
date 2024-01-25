package kr.co.ddoko.memberservice.exception.member;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }
}
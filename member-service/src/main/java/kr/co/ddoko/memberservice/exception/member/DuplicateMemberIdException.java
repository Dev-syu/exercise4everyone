package kr.co.ddoko.memberservice.exception.member;

public class DuplicateMemberIdException extends RuntimeException {

    public DuplicateMemberIdException(String message) {
        super(message);
    }
}

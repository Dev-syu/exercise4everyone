package kr.co.ddoko.memberservice.exception.team;

public class DuplicateTeamException extends RuntimeException {

    public DuplicateTeamException(String message) {
        super(message);
    }
}

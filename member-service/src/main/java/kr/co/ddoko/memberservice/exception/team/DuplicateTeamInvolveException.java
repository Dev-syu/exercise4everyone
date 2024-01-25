package kr.co.ddoko.memberservice.exception.team;

public class DuplicateTeamInvolveException extends RuntimeException {
    public DuplicateTeamInvolveException(String message) {
        super(message);
    }
}

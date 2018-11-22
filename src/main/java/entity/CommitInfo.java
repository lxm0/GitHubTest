package entity;

import java.sql.Blob;
import java.sql.Date;

public class CommitInfo {
    public Integer id;
    public String commitId;
    public String commitName;
    public Date commitTime;
    public String commitMessage;
    public Blob commitContent;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommitId() {
        return commitId;
    }

    public void setCommitId(String commitId) {
        this.commitId = commitId;
    }

    public String getCommitName() {
        return commitName;
    }

    public void setCommitName(String commitName) {
        this.commitName = commitName;
    }

    public Date getCommitTime() {
        return commitTime;
    }

    public void setCommitTime(Date commitTime) {
        this.commitTime = commitTime;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public Blob getCommitContent() {
        return commitContent;
    }

    public void setCommitContent(Blob commitContent) {
        this.commitContent = commitContent;
    }

    public CommitInfo() {
    }
}

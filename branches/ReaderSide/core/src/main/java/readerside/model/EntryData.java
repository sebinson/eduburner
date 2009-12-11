package readerside.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Index;

@Entity
@Table(name="entry_data")
public class EntryData extends EntityObject{

    private static final long serialVersionUID = 3326057871487171696L;
	private String grEntryId; // GrEntry id
    private String summary;
    private String content;

    @Index(name="grentry_id_index")
    public String getGrEntryId() {
        return grEntryId;
    }

    public void setGrEntryId(String grEntryId) {
        this.grEntryId = grEntryId;
    }

    @Column(name="summary_val", length = 200000)
    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Column(name="content_val", length = 200000)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "EntrySummary{" +
                "grEntryId='" + grEntryId + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (grEntryId != null ? grEntryId.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        return result;
    }
}

package app.novacodex.novacodex.model;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "key_entity")
public class KeyEntity {
    @Id
    private String key;
    private String type;
    private boolean revoked;

    @ManyToOne
    @JoinColumn(name = "university_id", nullable = false)
    private University university;

    // Геттеры/сеттеры
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public boolean isRevoked() { return revoked; }
    public void setRevoked(boolean revoked) { this.revoked = revoked; }

    public University getUniversity() { return university; }
    public void setUniversity(University university) { this.university = university; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyEntity keyEntity = (KeyEntity) o;
        return Objects.equals(key, keyEntity.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }
}
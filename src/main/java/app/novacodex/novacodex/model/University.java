    package app.novacodex.novacodex.model;

    import jakarta.persistence.*;

    @Entity
    @Table(name="universities")
    public class University {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true, nullable = false)
        private String uniCode;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUniCode() {
            return uniCode;
        }

        public void setUniCode(String uniCode) {
            this.uniCode = uniCode;
        }
    }
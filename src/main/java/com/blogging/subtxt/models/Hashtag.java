    package com.blogging.subtxt.models;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import java.util.HashSet;
    import java.util.Set;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Table(name = "hashtags")
    public class Hashtag {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(unique = true)
        private String tag;

        @ManyToMany(mappedBy = "hashtags", fetch = FetchType.LAZY)
        private Set<Tweet> tweets = new HashSet<>();

        public Hashtag(String tag) {
            this.tag = tag.toLowerCase().trim();
        }

        @PrePersist
        @PreUpdate
        private void formatTag() {
            if (tag != null) {
                this.tag = tag.toLowerCase().trim();
            }
        }
    }

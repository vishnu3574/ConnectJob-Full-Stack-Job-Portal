package com.connectjob.model;
import jakarta.persistence.*; import lombok.*; import java.time.LocalDateTime;
@Entity @Table(uniqueConstraints=@UniqueConstraint(columnNames={"job_id","user_id"})) @Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Application { @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id; @ManyToOne(optional=false) private Job job; @ManyToOne(optional=false) private User user; private String status="APPLIED"; private LocalDateTime appliedAt=LocalDateTime.now(); }

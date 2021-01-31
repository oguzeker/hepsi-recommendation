package com.hepsiburada.streamreader.entity;

import com.hepsiburada.streamreader.model.SourceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product_views")
@EntityListeners(AuditingEntityListener.class)
public class ProductView {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String messageId;

    private String userId;

    private String productId;

    @Enumerated(EnumType.ORDINAL)
    private SourceType sourceType;

    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;

}

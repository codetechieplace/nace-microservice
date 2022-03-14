
package com.nace.nacemicroservice.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;


@Data
@Builder
@Entity
@Table(name= "NACE_DETAILS")
@NoArgsConstructor
@AllArgsConstructor
public class NaceDetailsEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;

    @Column(name="ORDERS")
    private Long order;

    @Column(name="LEVEL")
    private Long level;

    @Column(name="CODE")
    private String code;

    @Column(name="PARENT")
    private String parent;

    @Column(name="DESCRIPTION")
    private String description;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="ITEM_INCLUDES")
    private String itemIncludes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="ITEM_ALSO_INCLUDES")
    private String itemAlsoIncludes;

    @Lob
    @Basic(fetch =FetchType.LAZY)
    @Column(name="RULINGS")
    private String rulings;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="ITEM_EXCLUDES")
    private String itemExcludes;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name="REFERENCES_ISIC")
    private String referenceIsic;

  /*  @Override
    public String toString(){
        return "NaceExcelDetails [id="

    } */
}

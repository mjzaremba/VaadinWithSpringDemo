package org.vaadin.spring.tutorial.Models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "tblPrismaEntry")
@Getter
@Setter
public class PrismaEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer idNumber;
    private String printer;
    private String filename;
    //Information from filename:
    public String clientName;
    public String sla;
    public String posyId;
    public int documentsCount;
    public String staple;
    public String year;
}

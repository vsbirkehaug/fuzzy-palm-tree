package com.groupb.artrec.models;

import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by VSB on 23/02/2016.
 */
public class Article {

    private String title;
    private Date publicationDate;
    private Reference reference;
    private ArrayList<Publisher> publishers;

    public ArrayList<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(ArrayList<Publisher> publishers) {
        this.publishers = publishers;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Reference getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}

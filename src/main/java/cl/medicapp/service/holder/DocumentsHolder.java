package cl.medicapp.service.holder;

import cl.medicapp.service.document.CommuneDocument;
import cl.medicapp.service.document.NationalityDocument;
import cl.medicapp.service.document.RoleDocument;

import java.util.List;

public class DocumentsHolder {

    private DocumentsHolder() {

    }

    private final static DocumentsHolder instance = new DocumentsHolder();

    public static DocumentsHolder getInstance() {
        return instance;
    }

    private List<CommuneDocument> communeDocumentList;
    private List<RoleDocument> roleDocumentList;
    private List<NationalityDocument> nationalityDocumentList;

    public List<CommuneDocument> getCommuneDocumentList() {
        return communeDocumentList;
    }

    public void setCommuneDocumentList(List<CommuneDocument> communeDocumentList) {
        this.communeDocumentList = communeDocumentList;
    }

    public List<RoleDocument> getRoleDocumentList() {
        return roleDocumentList;
    }

    public void setRoleDocumentList(List<RoleDocument> roleDocumentList) {
        this.roleDocumentList = roleDocumentList;
    }

    public List<NationalityDocument> getNationalityDocumentList() {
        return nationalityDocumentList;
    }

    public void setNationalityDocumentList(List<NationalityDocument> nationalityDocumentList) {
        this.nationalityDocumentList = nationalityDocumentList;
    }
}

package cl.medicapp.service.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity Message Chat
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "chat")
public class MessageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Identificador
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    /**
     * De
     * DBRef para referenciar entity User
     */
    @DBRef
    private UserEntity from;

    /**
     * Para
     * DBRef para referenciar entity User
     */
    @DBRef
    private UserEntity to;

    /**
     * Mensajes
     */
    private String message;

    /**
     * Fecha
     */
    @CreatedDate
    private Date date;

    /**
     * Fue leido
     */
    private boolean alreadyRead = false;

}

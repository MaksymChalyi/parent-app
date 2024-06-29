package com.maksimkaxxl.messagesendermicroservice.models;

import com.maksimkaxxl.messagesendermicroservice.models.enums.EmailStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;


@Getter
@Setter
@Document(indexName = "emails")
public class EmailMessage {

    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String subject;

    @Field(type = FieldType.Text)
    private String content;

    @Field(type = FieldType.Keyword)
    private String recipientEmail;

    @Field(type = FieldType.Text)
    private EmailStatus emailStatus;

    @Field(type = FieldType.Text)
    private String errorMessage;

    @Field(type = FieldType.Integer)
    private int retryCount;

    @Field(type = FieldType.Date)
    private Instant lastAttemptTime;
}

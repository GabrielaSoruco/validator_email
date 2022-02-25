
# Validator emails

This API checks that the email has the correct characters for a new username

## API Reference

#### Get all valid emails

```http
  GET /emailsValid 
```

#### Get all not valid emails 

```http
  GET /emailsNotValid
```

#### Get email by id number

```http
  GET /emails/{id}
```
#### Get a string indicating whether the email sent as a parameter is valid or not

```http
  GET /emails/validate/{email}
```

#### Get the number of validation attempts and the date

```http
  GET /emails/validate/intentos
```

#### POST an email, if it is verified that the email is valid, it saves the email otherwise it does not enter the record to the database

```http
  POST /emails/validate/add
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `correo`      | `string` | **Required**|





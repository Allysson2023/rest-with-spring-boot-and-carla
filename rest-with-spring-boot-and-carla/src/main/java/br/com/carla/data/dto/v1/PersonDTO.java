package br.com.carla.data.dto.v1;

//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.com.carla.serializer.GenderSerializer;
import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

// mostra as ordem dos campo dos json
//@JsonPropertyOrder({"id","sobrenome","nome", "endereço", "gende"})
//@JsonFilter("PersonFilter")
public class PersonDTO implements Serializable {

    private static final long serialVersionUIS = 1L;

    private Long id;
    private String nome;

    //@JsonInclude(JsonInclude.Include.NON_NULL)
    private String sobrenome;

    //@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String phoneNumber;

    //@JsonFormat(pattern = "dd/MM/yyyy")
    private Date birthDay;

    // formata o nome, deixa o jeito que voce deseja o nome:
    //@JsonProperty("endereço")
    private String address;

    // ignora o campo do json
    //@JsonIgnore
    //@JsonSerialize(using = GenderSerializer.class)
    private String gende;


    private String sensitiveData;

    public PersonDTO() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGende() {
        return gende;
    }

    public void setGende(String gende) {
        this.gende = gende;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    public String getSensitiveData() {
        return sensitiveData;
    }

    public void setSensitiveData(String sensitiveData) {
        this.sensitiveData = sensitiveData;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTO personDTO)) return false;
        return Objects.equals(getId(),
                personDTO.getId()) && Objects.equals(getNome(),
                personDTO.getNome()) && Objects.equals(getSobrenome(),
                personDTO.getSobrenome()) && Objects.equals(getPhoneNumber(),
                personDTO.getPhoneNumber()) && Objects.equals(getBirthDay(),
                personDTO.getBirthDay()) && Objects.equals(getAddress(),
                personDTO.getAddress()) && Objects.equals(getGende(), personDTO.getGende())
                && Objects.equals(getSensitiveData(), personDTO.getSensitiveData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(),
                getNome(), getSobrenome(),
                getPhoneNumber(), getBirthDay(),
                getAddress(), getGende(), getSensitiveData());
    }
}

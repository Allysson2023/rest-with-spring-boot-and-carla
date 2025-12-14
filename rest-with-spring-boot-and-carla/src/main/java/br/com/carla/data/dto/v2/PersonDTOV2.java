package br.com.carla.data.dto.v2;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class PersonDTOV2 implements Serializable {

    private static final long serialVersionUIS = 1L;

    private Long id;
    private String nome;
    private String sobrenome;
    private Date birthDay;
    private String address;
    private String gende;

    public PersonDTOV2() {}

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PersonDTOV2 that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getNome(),
                that.getNome()) && Objects.equals(getSobrenome(), that.getSobrenome())
                && Objects.equals(getBirthDay(), that.getBirthDay()) && Objects.equals(getAddress(),
                that.getAddress()) && Objects.equals(getGende(), that.getGende());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNome(), getSobrenome(), getBirthDay(), getAddress(), getGende());
    }
}

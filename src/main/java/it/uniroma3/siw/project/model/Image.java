package it.uniroma3.siw.project.model;

import jakarta.persistence.*;

import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;

@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private byte[] bytes;

    @Lob
    private String base64Image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getBase64Image() {
        return base64Image;
    }

    public void setBase64Image(String base64Image) {
        this.base64Image = base64Image;
    }

    public Image(){

    }

    public Image(byte[] bytes){
        this.bytes = bytes;
        this.setBase64Image(Base64.getEncoder().encodeToString(this.bytes));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image photo = (Image) o;
        return Objects.equals(id, photo.id) && Arrays.equals(bytes, photo.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}

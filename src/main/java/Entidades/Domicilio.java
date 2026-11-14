package Entidades;

public class Domicilio {
    private String provincia;
    private String ciudad;
    private String calle;
    private int altura;
    private int piso;
    private int depto;

    //Constructor
    public Domicilio(String provincia, String ciudad, String calle, int altura, int piso, int depto) {
        this.provincia = provincia;
        this.ciudad = ciudad;
        this.calle = calle;
        this.altura = altura;
        this.piso = piso;
        this.depto = depto;
    }

    //Getters y Setters
    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        this.piso = piso;
    }

    public int getDepto() {
        return depto;
    }

    public void setDepto(int depto) {
        this.depto = depto;
    }

    @Override
    public String toString() {
        return "Domicilio: " +
                " | Provincia: " + provincia + '\'' +
                " | Ciudad: " + ciudad + '\'' +
                " | Calle: " + calle + '\'' +
                " | Altura: " + altura +
                " | Piso: " + piso +
                " | Dpto: " + depto;
    }
}

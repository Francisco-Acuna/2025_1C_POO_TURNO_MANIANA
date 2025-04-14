package ar.org.centro8.curso.java.entidades.herencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Direccion {
    private String calle;
    private int numero;
    private String piso;
    private String departamento;
    private String ciudad;

    /**
     * Constructor para crear una dirección de CABA
     * @param calle
     * @param numero
     * @param piso
     * @param departamento
     */
    public Direccion(String calle, int numero, String piso, String departamento) {
        this.calle = calle;
        this.numero = numero;
        this.piso = piso;
        this.departamento = departamento;
        this.ciudad = "CABA";
    }

    

}

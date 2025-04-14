package ar.org.centro8.curso.java.entidades.herencia;

import ar.org.centro8.curso.java.entidades.relaciones.Cuenta;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Cliente extends Persona{
    private int numeroCliente;
    private Cuenta cuenta;
    
    public Cliente(String nombre, String apellido, int edad, Direccion direccion, int numeroCliente, Cuenta cuenta) {
        super(nombre, apellido, edad, direccion);
        this.numeroCliente = numeroCliente;
        this.cuenta = cuenta;
    }

    @Override
    public void saludar() {
        System.out.println("Hola, soy un cliente!");
    }


}

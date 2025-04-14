package ar.org.centro8.curso.java.entidades.herencia;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class Empleado extends Persona {
    //al heredar, la clase está obligada a implementar los métodos abstractos 
    private int legajo;
    private double sueldoBasico;

    public Empleado(String nombre, String apellido, int edad, Direccion direccion, int legajo, double sueldoBasico) {
        super(nombre, apellido, edad, direccion);
        this.legajo = legajo;
        this.sueldoBasico = sueldoBasico;
    }

    @Override
    public void saludar() {
        System.out.println("Hola, soy un empleado!");
    }


}

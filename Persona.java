package ar.org.centro8.curso.java.entidades.herencia;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public abstract class Persona {
    //no se pueden crear objetos de una clase abstracta
    private String nombre;
    private String apellido;
    private int edad;
    private Direccion direccion;

    /*public void saludar(){
        System.out.println("Hola, soy una persona!");
    } */

    public abstract void saludar();

}

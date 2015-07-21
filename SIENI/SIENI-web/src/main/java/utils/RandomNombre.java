/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author francisco_medina
 */
public class RandomNombre {

    private String[] nombre = {"juan", "nelson", "carlos", "josue", "felipe", "paco", "orlando", "vicente", "armando", "miguel", "francisco", "gabriel"};
    private String[] apellidos = {"archila", "mejia", "alvarenga", "zuniga", "erazo", "medina", "aguillon", "renderos", "malcia", "fernandez", "herrera"};

    public String getRandomNombre() {
        return nombre[((int) (Math.random() * (nombre.length) + 1)) - 1] + " " + nombre[((int) (Math.random() * (nombre.length) + 1)) - 1] + " " + nombre[((int) (Math.random() * (apellidos.length) + 1)) - 1] + " " + nombre[((int) (Math.random() * (apellidos.length) + 1)) - 1];
    }
}
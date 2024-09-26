package org.example;

public class Variable extends Expression{
    private final String name;

    public Variable(String name,IPrintable printable) {
        super(printable);
        this.name = name;
    }

    public void print() {
        printable.print(name);
    }

    public Expression derivative(String variable) {
        if (name.equals(variable)) {
            return new Number (1,printable);
        }
        else {
            return new Number (0,printable);
        }
    }

    public int eval(String vars) {
        String[] assignments = vars.split(";");
        for (String assignment : assignments) {
            String[] pair = assignment.split("=");
            if (pair[0].trim().equals(name)) {
                return Integer.parseInt(pair[1].trim());
            }
        }
        System.out.println("Variable " + name + " not found, answer incorrect\n");
        return -1;
    }
}
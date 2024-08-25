package dummy;

class Hello {

    public static void main(String[] args) {

        System.out.println(new Tree.Empty());

        System.out.println(new Tree.Node(1, new Tree.Empty(), new Tree.Empty()));
    }
}

sealed interface Tree<T> {
    record Empty() implements Tree {}
    record Node(int value, Tree left, Tree right) implements Tree {}
}

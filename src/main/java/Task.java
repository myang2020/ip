abstract class Task {

    protected Boolean done;
    protected final String name;
    protected Type type;
    enum Type {
        T,D,E;

        @Override
        public String toString() {
            return name(); // Returns the name of the enum constant (e.g., "T", "D", "E")
        }
    }

    Task(String name, Type type) {
        this.done = false;
        this.name = name;
        this.type = type;
    }

    // Mark task as done
    public void mark() {
        this.done = true;
    }

    // Unmark task
    public void unmark() {
        this.done = false;
    }
    public String getName() {
        return this.name;
    }


}

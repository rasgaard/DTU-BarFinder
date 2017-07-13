package dk.pfpressere.dtu_barfinder;

public class DestinationManager {

    private DestinationList destinations;

    public DestinationManager() {
        destinations = new DestinationList();
    }

    public Destination getNextDestination() {
        return destinations.next();
    }

    public Destination getPreviousDestination() {
        return destinations.previous();
    }
    
    public Destination getCurrentDestination() {
        return destinations.current();
    }

    public void addDestination(Destination destination) {
        destinations.add(destination);
    }


    private class DestinationList {
        // An implementation of a linked list that wraps around.

        private Link first;
        private Link last;
        private int size;

        private Link current;

        public DestinationList() {
            size = 0;
        }

        public void add(Destination destination) {
            Link link = new Link(destination);
            if(first == null) {
                // The list is empty.
                first = link;
                link.next = link;
                link.previous = link;
                current = link;
            } else {
                // The list is not empty.
                last.next = link;
                first.previous = link;
                link.previous = last;
                link.next = first;
            }
            last = link;
            size++;
        }

        public Destination getFirst() {
            return first.destination;
        }

        public Destination getLast() {
            return last.destination;
        }

        public Destination next() {
            current = current.next;
            return current.destination;
        }

        public Destination getDestination(String name) {
            // Implement using hashmap or something.
            Link link = current;
            for(int i=0;i<size;i++) {
                if(link.destination.getName().equals(name)) {
                    return link.destination;
                } else {
                    link = link.next;
                }
            }
            // Returns null if nothing was found with given name.
            return null;
        }

        public Destination previous() {
            current = current.previous;
            return current.destination;
        }

        public Destination current() {
            return current.destination;
        }

        private class Link {
            public Destination destination;

            public Link next;
            public Link previous;

            public Link(Destination destination) {
                this.destination = destination;
            }
        }
    }


}

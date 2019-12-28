/*
 * © 2019 CSE2010 HW #2
 *
 * You can freely modify this class except the signatures of the public methods!!
 */

import java.util.Arrays;
import java.util.stream.Collectors;

class Term {
    /*
     * Public field is a bad idea. But, for the sake of simplicity .....
     */
    public double   coeff;  // coefficient
    public int      expo;   // exponent

    public Term(double coeff, int expo) {
        this.coeff = coeff;
        this.expo = expo;
    }

    public static int compare(Term t1, Term t2) {
        if (t1.expo > t2.expo)
            return 1;
        if (t1.expo < t2.expo)
            return -1;
        return 0;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Term))
            return false;
        final Term term = (Term) o;
        return Double.compare(term.coeff, coeff) == 0 &&
            expo == term.expo;
    }
}

/*
 * class DLinkedPolynomial
 */
public class DLinkedPolynomial implements Polynomial {

    private DLinkedList<Term> list = null;

    public DLinkedPolynomial() {
        list = new DLinkedList<Term>();
    }

    @Override
    public int getDegree() { // 다항식의 차수
        if (list.isEmpty()) return 0; // list의 size = 0

        Node<Term> p = list.getFirstNode();
        int result = p.getInfo().expo;
        for (int i = 0 ; i < list.size() - 1 ; i++) {
            if (result < p.getNext().getInfo().expo) result = p.getNext().getInfo().expo;
            p = p.getNext();
        }
        return result;
    }

    @Override
    public double getCoefficient(final int expo) {
        double result = 0;

        Node<Term> p = list.getFirstNode();
        for (int i = 0 ; i < list.size() ; i++) {
            if (p.getInfo().expo == expo) result = p.getInfo().coeff;
            p = p.getNext();
        }
        return result;
    }

    @Override
    public Polynomial add(final Polynomial p) {
        DLinkedPolynomial result = new DLinkedPolynomial();
        Node<Term> tmp = list.getFirstNode();

        for (int i = 0 ; i < list.size() ; i++) {
            result.addTerm(tmp.getInfo().coeff, tmp.getInfo().expo);
            tmp = list.getNextNode(tmp);
        }

        DLinkedPolynomial doublyLinkedp = (DLinkedPolynomial) p;
        Node<Term> nodeOfp = doublyLinkedp.list.getFirstNode();

        for (int i = 0 ; i < doublyLinkedp.list.size() ; i++) {
            Node<Term> nodeOfResult = result.list.getFirstNode();
            Term newTerm = new Term(nodeOfp.getInfo().coeff, nodeOfp.getInfo().expo);
            Node<Term> newNode = new Node(newTerm, null, null);

	    	for (int j = 0 ; j < result.list.size() ; j++) {
	    		if (nodeOfResult.getInfo().expo < nodeOfp.getInfo().expo) {
	    			result.list.addBefore(nodeOfResult, newNode);
	    			break;
	    		}
	    		else if(nodeOfResult.getInfo().expo == nodeOfp.getInfo().expo) {
	    			nodeOfResult.getInfo().coeff += nodeOfp.getInfo().coeff;
	    			break;
	    		}
	    		else if(result.list.getNextNode(nodeOfResult) == null) {
	    			result.list.addAfter(nodeOfResult, newNode);
	    			break;
	    		}
	    		nodeOfResult = result.list.getNextNode(nodeOfResult);
	    	}

            nodeOfp = nodeOfp.getNext();
        }

        return result;
    }

    @Override
    public Polynomial mult(final Polynomial p) {
        DLinkedPolynomial result = new DLinkedPolynomial();
        DLinkedPolynomial doublyLinkedp = (DLinkedPolynomial) p;
        Node<Term> nodeOfp = doublyLinkedp.list.getFirstNode();

        for (int i = 0; i < doublyLinkedp.list.size() ; i++) {
            Node<Term> tmp = list.getFirstNode();

            for (int j = 0 ; j < list.size() ; j++) {
                result.addTerm(tmp.getInfo().coeff * nodeOfp.getInfo().coeff, tmp.getInfo().expo + nodeOfp.getInfo().expo);
                tmp = tmp.getNext();
            }

            nodeOfp = nodeOfp.getNext();
        }

        return result;
    }

    @Override
    public void addTerm(final double coeff, int expo) {
        if (coeff == 0) return;

        Term newTerm = new Term(coeff, expo); // 새 항
        Node<Term> newNode = new Node(newTerm, null, null); // 새 노드

        if (list.isEmpty()) list.addFirst(newNode);

        else {
            Node<Term> p = list.getFirstNode();
            for (int i = 0 ; i < list.size() ; i++) {
                if (newNode.getInfo().expo > p.getInfo().expo) {
                    list.addBefore(p, newNode);
                    return;
                }
                else if (newNode.getInfo().expo == p.getInfo().expo) {
                    p.getInfo().coeff += newNode.getInfo().coeff;
                    return;
                }
                else if (list.getNextNode(p) == null) {
                    list.addAfter(p, newNode);
                    return;
                }
                p = list.getNextNode(p);
            }
        }
    }

    @Override
    public void removeTerm(final int expo) {
        Node<Term> p =  list.getFirstNode();
        try {
            for (int i = 0 ; i <= list.size() ; i++) {
                if (p.getInfo().expo == expo) {
                    list.remove(p);
                    return;
                }
                p = p.getNext();
            }
        }catch(Exception o) {
            NoSuchTermExistsException e = new NoSuchTermExistsException();
            throw e;
        }
    }

    @Override
    public double evaluate(final double val) {
        double result = 0;

        Node<Term> p = list.getFirstNode();
        for (int i = 0 ; i < list.size() ; i++) {
            result += p.getInfo().coeff * Math.pow(val, p.getInfo().expo);
            p = p.getNext();
        }

        return result;
    }

    @Override
    public int termCount() {
        return list.size();
    }

    @Override
    public String toString() {
        if (list.isEmpty())
            return "Empty Polynomial";
        else {
            String[] terms = new String[termCount()];
            int i = 0;
            Node<Term> current = list.getFirstNode();
            do {
                terms[i++] = current.getInfo().coeff + "x^" + current.getInfo().expo;
                current = list.getNextNode(current);
            } while (current != null);
            return Arrays.stream(terms).collect(Collectors.joining("+"));
        }
    }

}


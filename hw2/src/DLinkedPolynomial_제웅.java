/*
 * © 2019 CSE2010 HW #2
 *
 * You can freely modify this class except the signatures of the public methods!!
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Scanner;
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
    public int getDegree() {
    	if(list.isEmpty()) {
    		return 0;
    	}
    	else {
    		Node<Term> node = list.getFirstNode();
    		int ans = node.getInfo().expo;
    		for(int i=0; i<list.size()-1; i++) {
    			if(ans < node.getNext().getInfo().expo) {
    				ans = node.getNext().getInfo().expo;
    			}
    			node = node.getNext();
    		}
    		return ans;
    	}
    }

    @Override
    public double getCoefficient(final int expo) {
    	
    	Node<Term> node = list.getFirstNode();
    	
    	for(int i=0; i<list.size(); i++) {
    		if(node.getInfo().expo == expo) {
    			return node.getInfo().coeff;
    		}
    		node = node.getNext();
    	}
    	return 0;
    }

    @Override
    public Polynomial add(final Polynomial p) {
    	
    	DLinkedPolynomial ans = new DLinkedPolynomial();
    	Node<Term> temp = list.getFirstNode();
    	
    	for(int i=0; i<this.list.size(); i++) {
    		ans.addTerm(temp.getInfo().coeff, temp.getInfo().expo);
    		temp = list.getNextNode(temp);
    	}
    	
    	DLinkedPolynomial dl_p = (DLinkedPolynomial) p;
    	Node<Term> p_node = dl_p.list.getFirstNode();
    	
    	for(int i=0; i<dl_p.list.size(); i++) {
    		
    		Node<Term> node = ans.list.getFirstNode();
    		Term newterm = new Term(p_node.getInfo().coeff, p_node.getInfo().expo);
        	Node<Term> newnode = new Node(newterm,null,null);
    		
	    	for(int j=0; j<ans.list.size(); j++) {
	    		if(node.getInfo().expo < p_node.getInfo().expo) {
	    			ans.list.addBefore(node, newnode);
	    			break;
	    		}
	    		else if(node.getInfo().expo == p_node.getInfo().expo) {
	    			node.getInfo().coeff += p_node.getInfo().coeff;
	    			break;
	    		}
	    		else if(ans.list.getNextNode(node) == null) {
	    			ans.list.addAfter(node, newnode);
	    			break;
	    		}
	    		node = ans.list.getNextNode(node);
	    	}
	    	
	    	p_node = p_node.getNext();
    	}
    	return ans;
    }

    @Override
    public Polynomial mult(final Polynomial p) {
    	
    	DLinkedPolynomial ans = new DLinkedPolynomial();
    	
    	DLinkedPolynomial dl_p = (DLinkedPolynomial) p;
    	Node<Term> p_node = dl_p.list.getFirstNode();
    	
    	for(int i=0; i<dl_p.list.size(); i++) {
    		
    		Node<Term> node = list.getFirstNode();
        	
    		for(int j=0; j<list.size(); j++) {
    			double newcoeff = node.getInfo().coeff * p_node.getInfo().coeff;
    			int newexpo = node.getInfo().expo + p_node.getInfo().expo;
    			ans.addTerm(newcoeff, newexpo);
    			node = node.getNext();
    		}
    		p_node = p_node.getNext();
    	}
    	
    	return ans;
    }

    @Override
    public void addTerm(final double coeff, int expo) {
    	
    	Term newterm = new Term(coeff, expo);
    	Node<Term> newnode = new Node(newterm,null,null);
    	
    	
    	if(list.isEmpty()) {
    		list.addFirst(newnode);
    	}
    	else {
    		Node<Term> node = list.getFirstNode();
    		for(int i=0; i<list.size(); i++) {
    			if(newnode.getInfo().expo > node.getInfo().expo) {
    				list.addBefore(node, newnode);
    				break;
    			}
    			else if(newnode.getInfo().expo == node.getInfo().expo) {
    				node.getInfo().coeff += newnode.getInfo().coeff;
    				break;
    			}
    			else if(list.getNextNode(node) == null) {
    				list.addAfter(node, newnode);
    				break;
    			}
    			node = list.getNextNode(node);
    		}
    	}
    	    	
    }

    @Override
    public void removeTerm(final int expo) {
    	
    	Node<Term> node = list.getFirstNode();
    	try {
    		for(int i=0; i<=list.size(); i++) {
        		if(node.getInfo().expo == expo) {
        			list.remove(node);
        			break;
        		}
        		node = node.getNext();
        	}
    	}catch(Exception o){
    		NoSuchTermExistsException e = new NoSuchTermExistsException();
    		throw e;
    	}
		
    	
    }

    @Override
    public double evaluate(final double val) {
    	double ans = 0;
    	Node<Term> node = list.getFirstNode();
    	for(int i=0; i<this.list.size(); i++) {
    		ans += node.getInfo().coeff * Math.pow(val, node.getInfo().expo);
    		node = node.getNext();
    	}
    	System.out.println(ans);
    	return ans;
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

    public static void main(String[] args) {
    	Polynomial f = new DLinkedPolynomial();

        f.addTerm(3.0, 2);
        f.addTerm(7.0, 0);

        assertThrows(NoSuchTermExistsException.class, () -> f.removeTerm(1));
	}
}


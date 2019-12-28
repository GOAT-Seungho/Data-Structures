import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PolyTest {

    @Test
    void should_generate_polynomials_with_single_term() {

        Poly poly = new Poly(1);
        poly.addTerm(1, 0);

        assertEquals("1x^0", poly.toString());
        assertEquals(0, poly.degree());
        assertEquals(1, poly.getTermCount());

        poly = new Poly(1);
        poly.addTerm(2, 1);

        assertEquals("2x^1", poly.toString());
        assertEquals(1, poly.degree());
        assertEquals(1, poly.getTermCount());
    }

    @Test
    void should_generate_polynomials_with_multiple_terms_ascending_order() {

        Poly poly = new Poly(3);
        poly.addTerm(1, 0);
        poly.addTerm(2, 1);
        poly.addTerm(3, 2);

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_generate_polynomials_with_multiple_terms_case_descending_order() {

        Poly poly = new Poly(3);
        poly.addTerm(3, 2);
        poly.addTerm(2, 1);
        poly.addTerm(1, 0);

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_generate_polynomials_with_multiple_terms_random_order() {

        Poly poly = new Poly(3);
        poly.addTerm(2, 1);
        poly.addTerm(3, 2);
        poly.addTerm(1, 0);

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_generate_polynomials_with_vararg_terms() {

        Poly poly = new Poly(3, new Term(2,1), new Term(3,2), new Term(1,0));

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_generate_identical_polynomial_when_added_by_unit_polynomial() {

        Poly poly = new Poly(3);
        poly.addTerm(3, 2);
        poly.addTerm(2, 1);
        poly.addTerm(1, 0);

        Poly unitPoly = new Poly(0);

        assertEquals("3x^2 + 2x^1 + 1x^0", poly.add(unitPoly).toString());
        assertEquals(2, poly.degree());
        assertEquals(3, poly.getTermCount());
    }

    @Test
    void should_add_two_polynomials() {

        Poly poly1 = new Poly(4);
        poly1.addTerm(1, 3);
        poly1.addTerm(2, 2);
        poly1.addTerm(3, 1);
        poly1.addTerm(4, 0);

        Poly poly2 = new Poly(4, new Term(2,1), new Term(1,0), new Term(4,3));

        Poly poly3 = poly1.add(poly2);

        assertEquals("5x^3 + 2x^2 + 5x^1 + 5x^0", poly3.toString());
        assertEquals(3, poly3.degree());
        assertEquals(4, poly3.getTermCount());
    }

    @Test
    void should_multiply_two_polynomials() {
        Poly poly1 = new Poly(4);
        poly1.addTerm(1, 3);
        poly1.addTerm(2, 2);
        poly1.addTerm(3, 1);
        poly1.addTerm(4, 0);

        Poly poly2 = new Poly(4);
        poly2.addTerm(2, 1);
        poly2.addTerm(1, 0);
        poly2.addTerm(4, 3);

        Poly poly3 = poly1.mult(poly2);

        assertEquals("4x^6 + 8x^5 + 14x^4 + 21x^3 + 8x^2 + 11x^1 + 4x^0", poly3.toString());
        assertEquals(6, poly3.degree());
        assertEquals(7, poly3.getTermCount());
    }
}

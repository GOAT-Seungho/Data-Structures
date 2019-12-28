import java.util.Collection;
import java.util.Set;

/**
 * Link-based implementation of binary search tree
 *
 * @param <K> the key type
 * @param <V> the value type
 */
public class LinkedBinarySearchTree<K extends Comparable<K>, V>
        extends LinkedBinaryTree<Entry<K, V>> implements OrderedMap<K, V> {

    @Override public Set<K> keys() {

    }

    @Override public Collection<V> values() {

    }

    @Override public Collection<Entry<K, V>> entries() {

    }

    @Override
    public Entry<K, V> firstEntry() {
        Position<Entry<K,V>> p = root();
        if(!isEmpty()){
            return null;
        }
        while(p.hasLeft()){
            p=p.left();
        }
        return p.element();
    }

    @Override
    public Entry<K, V> lastEntry() {
        if (isEmpty()) throw new IllegalStateException("Empty tree");
        Position<Entry<K,V>> p = root();
        while(p.hasRight()){
            p=p.right();
        }
        return p.element();
    }

    @Override
    public Entry<K, V> floorEntry(K key) {
        if (isEmpty()) throw new IllegalStateException("Empty tree");
        Position<Entry<K,V>> p = root();
        while(true){
            int comp = ((Comparable)key).compareTo(p.element().key);
            if(comp == 0){
                return p.element();
            }
            else if(comp > 0){
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 > 0){
                        break;
                    }
                    if(comp1 < 0){
                        p = p.parent();
                        break;
                    }
                }
                p=p.right();
            }
            else{
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 > 0){
                        p = p.parent();
                        break;
                    }
                    if(comp1 < 0){
                        break;
                    }
                }
                p = p.left();
            }
        }
        return p.element();
    }

    @Override
    public Entry<K, V> lowerEntry(K key) {
        if (isEmpty()) throw new IllegalStateException("Empty tree");
        Position<Entry<K,V>> p = root();
        while(true){
            int comp = ((Comparable)key).compareTo(p.element().key);
            if(comp == 0){
                p=p.left();
                while(p.hasRight()){
                    p=p.right();
                }
                break;
            }
            else if(comp > 0){
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 < 0){
                        p=p.parent();
                        break;
                    }
                    else{
                        break;
                    }   
                }
                p=p.right();
            }
            else{
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 > 0){
                        p = p.parent();
                        break;
                    }
                    else{
                        break;
                    }
                }
                p = p.left();
            }
        }
        return p.element();
    }

    @Override
    public Entry<K, V> ceilingEntry(K key) {
        if (isEmpty()) throw new IllegalStateException("Empty tree");
        Position<Entry<K,V>> p = root();
        while(true){
            int comp = ((Comparable)key).compareTo(p.element().key);
            if(comp == 0){
                return p.element();
            }
            else if(comp > 0){
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 < 0){
                        p=p.parent();
                        break;
                    }
                    else{
                        break;
                    }   
                }
                p=p.right();
            }
            else{
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 > 0){
                        p = p.parent();
                        break;
                    }
                    else{
                        break;
                    }
                }
                p = p.left();
            }
        }
        return p.element();
    }

    @Override
    public Entry<K, V> higherEntry(K key) {
        if (isEmpty()) throw new IllegalStateException("Empty tree");
        Position<Entry<K,V>> p = root();
        while(true){
            int comp = ((Comparable)key).compareTo(p.element().key);
            if(comp == 0){
                p=p.right();
                while(p.hasLeft()){
                    p=p.left();
                }   
                break;
            }
            else if(comp > 0){
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 < 0){
                        p=p.parent();
                        break;
                    }
                    else{
                        break;
                    }   
                }
                p=p.right();
            }
            else{
                if(isExternal(p)){
                    int comp1 = ((Comparable)key).compareTo(p.element().key);
                    if(comp1 > 0){
                        p = p.parent();
                        break;
                    }
                    else{
                        break;
                    }
                }
                p = p.left();
            }
        }
        return p.element();
        return null;
    }

    @Override
    public Entry<K, V> get(K key) {
        Position<Entry<K,V>> p = root();
        while(true){
            int comp = ((Comparable)key).compareTo(p.element().key);
            if(comp == 0){
                return p.element().value;
            }
            else if(comp > 0){
                if(isExternal(p)){
                    return null;
                }
                p=p.right();
            }
            else{
                if(isExternal(p)){
                    return null;
                }
                p=p.left();
            }
        }        
        return null;
    }

    @Override
    public V put(K key, V value) {
        Entry<K,V> newentry = new Entry<K,V>(key, value);
        if(isEmpty()){
            addroot(newentry);
        }
        else{
            Position<Entry<K,V>> p = root();
            while(isInternal(p)){
                int comp = ((Comparable)key).compareTo(p.element().key);
                if(comp == 0){
                    break;
                }
                else if(comp < 0){    
                    p = p.left();
                }
                else{
                    p=p.right();
                }
            }
            if(isExternal(p)){
                expandExternal(p, newentry);
            }
            else{
                p.replace(newentry);
            }
        }
    }

    @Override
    public V remove(K key) {
        Position<Entry<K,V>> p = root();
        Position<Entry<K,V>> q;
        boolean exist = false;
        while(p.isInternal()) {
            int comp = ((Comparable)key).compareTo(p.element().key);
            if(comp == 0) {
                exist = true;
                break;
            }
            else if(comp > 0 && p.hasRight()) p = p.right();
            else if(comp < 0 && p.hasLeft()) p = p.left();
            else break;
        }
        if(exist){
        //자식노드가 없을 경우
            if(p.isExternal()){
                if(isLeftChild(p)){
                    p.parent().setLeft(null);
                }
                else if(isRightChild(p)){
                    p.parent().setRight(null);
                }
                else if(isRoot(p)){
                    setRoot(null);
                }
            }    

        //자식노드가 두개일경우
            else if(p.hasLeft() && p.hasRight()){
                Position<Entry<K,V>> q = p.right();
                while(q.hasLeft()){
                    q=q.left();
                }
                remove(q.element().key);
                p.replace(q.element())
            }
        //자식노드가 한개일 경우
            else if(p.hasLeft()){
                if(isRoot(p)){
                    setRoot(p.left());
                }
                else if(isLeftChild(p)){
                    p.parent().setLeft(p.left());
                }
                else if(isRightChild(p)){
                    p.parent().setRight(p.left());
                }
            }
            else if(p.hasRight()){
                if(isRoot(p)){
                    setRoot(p.right());
                }
                else if(isLeftChild(p)){
                    p.parent().setLeft(p.right());
                }
                else if(isRightChild(p)){
                    p.parent().setRight(p.right());
                }
            }
            
        }
    }

}

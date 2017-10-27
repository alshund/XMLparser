package entity;

import java.util.List;

public interface XMLNode {
    int getDepth();
    List<XMLNode> getElements();
}

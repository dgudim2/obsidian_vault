<<<<<<<< HEAD:uni/Java/randomshop/src/main/java/org/kloud/daos/FileProductsDAO.java
package org.kloud.daos;
|||||||| 5e0c109:uni/Java/randomshop/src/main/java/org/kloud/utils/FileProductsDAO.java
package org.kloud.utils;
========
package org.kloud.daos.file;
>>>>>>>> e6c33dacc3b30b2b9e3c9703182514ce4bdcf36e:uni/Java/randomshop/src/main/java/org/kloud/daos/file/FileProductsDAO.java

import org.jetbrains.annotations.NotNull;
import org.kloud.model.product.Product;

/**
 * {@link BasicFileDAO} for {@link Product Products}
 */
public class FileProductsDAO extends BasicFileDAO<Product> {
    @Override
    @NotNull
    protected String getFileName() {
        return "products.dat";
    }
}

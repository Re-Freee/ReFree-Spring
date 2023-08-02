package backend.refree.module.Analysis;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KomoranUtils {

    public static final Logger log = LoggerFactory.getLogger(KomoranUtils.class);

    private KomoranUtils(){}

    public static Komoran getInstance() {
        return KomoranInstance.instance;
    }

    private static class KomoranInstance {
        public static final Komoran instance = new Komoran(DEFAULT_MODEL.FULL);
    }
}

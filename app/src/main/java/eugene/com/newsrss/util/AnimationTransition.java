package eugene.com.newsrss.util;

import android.support.transition.ChangeBounds;
import android.support.transition.ChangeTransform;
import android.support.transition.TransitionSet;


public class AnimationTransition extends TransitionSet {
    public AnimationTransition() {
        setOrdering(ORDERING_SEQUENTIAL);
        addTransition(new ChangeBounds()).
                addTransition(new ChangeTransform());
    }
}

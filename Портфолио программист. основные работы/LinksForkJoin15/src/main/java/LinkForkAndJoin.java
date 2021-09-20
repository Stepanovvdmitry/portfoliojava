import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class LinkForkAndJoin extends RecursiveTask<Link> {
    private Link link;

    LinkForkAndJoin(Link link) {
        this.link = link;
    }

    @Override
    protected Link compute() {
        ArrayList<ForkJoinTask> taskList = new ArrayList<>();
        Set<Link> children = link.getChildren();
        children.forEach(
            child -> {
                taskList.add(
                    new LinkForkAndJoin(child)
                        .fork());

                try { Thread.sleep(100);}
                catch (InterruptedException e) {}

            });
        taskList.forEach(ForkJoinTask::join);
        return link;
    }
}

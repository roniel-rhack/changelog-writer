package dev.roniel.utils;

import com.vladsch.flexmark.ast.BulletList;
import com.vladsch.flexmark.ast.BulletListItem;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Text;
import dev.roniel.models.Change;
import dev.roniel.models.Version;

import java.util.List;

public class NodeFactory {
    public Heading createVersionHeading(Version version) {
        Heading versionHeading = new Heading();
        versionHeading.setLevel(2);
        versionHeading.appendChild(new Text(version.toString()));
        return versionHeading;
    }

    public Heading createChangeTypeHeading(ChangeType changeType) {
        Heading changeTypeHeading = new Heading();
        changeTypeHeading.setLevel(3);
        changeTypeHeading.appendChild(new Text(changeType.toString()));
        return changeTypeHeading;
    }

    public BulletList createBulletList(List<Change> changes) {
        BulletList bulletList = new BulletList();
        for (Change change : changes) {
            BulletListItem listItem = new BulletListItem();
            listItem.appendChild(new Text(change.getDescription()));
            bulletList.appendChild(listItem);
        }
        return bulletList;
    }
}


package Parser;

import ModelLayer.Project;

public class ProjectParser implements EntityParser<Project> {
    @Override
    public String toString(Project obj) {
        return obj.toString();
    }

    @Override
    public Project fromString(String data) {
        return Project.fromString(data);
    }
}

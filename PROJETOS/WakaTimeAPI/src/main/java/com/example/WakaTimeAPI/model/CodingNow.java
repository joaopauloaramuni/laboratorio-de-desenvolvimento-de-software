package com.example.WakaTimeAPI.model;

/**
 * "Codando agora": devolvido em JSON por GET /api/codando e desenhado pelo
 * fragmento templates/fragments/codando-agora.html.
 *
 * {
 *   "active": true,
 *   "todaySeconds": 15600,
 *   "last": { "isPublic": true, "project": "WakaTimeAPI", "branch": "main", ... }
 * }
 */
public class CodingNow {

    private final boolean active;
    private final long todaySeconds;
    private final Last last;

    public CodingNow(boolean active, long todaySeconds, Last last) {
        this.active = active;
        this.todaySeconds = todaySeconds;
        this.last = last;
    }

    public boolean isActive() {
        return active;
    }

    public long getTodaySeconds() {
        return todaySeconds;
    }

    /** Último heartbeat (null = nenhuma atividade no editor hoje). */
    public Last getLast() {
        return last;
    }

    public static class Last {

        private final boolean isPublic;
        private final String project;
        private final String branch;
        private final String language;
        private final String editor;
        private final long at;                  // ms
        private final long sessionStart;        // ms
        private final long projectTodaySeconds;

        public Last(boolean isPublic, String project, String branch, String language, String editor,
                    long at, long sessionStart, long projectTodaySeconds) {
            this.isPublic = isPublic;
            this.project = project;
            this.branch = branch;
            this.language = language;
            this.editor = editor;
            this.at = at;
            this.sessionStart = sessionStart;
            this.projectTodaySeconds = projectTodaySeconds;
        }

        /** Projeto na lista de públicos: aparece pelo nome e com a branch. */
        public boolean getIsPublic() {
            return isPublic;
        }

        /** null quando o projeto é privado. */
        public String getProject() {
            return project;
        }

        /** null quando o projeto é privado. */
        public String getBranch() {
            return branch;
        }

        public String getLanguage() {
            return language;
        }

        public String getEditor() {
            return editor;
        }

        public long getAt() {
            return at;
        }

        public long getSessionStart() {
            return sessionStart;
        }

        public long getProjectTodaySeconds() {
            return projectTodaySeconds;
        }

        /** Duração da sessão atual, do primeiro ao último heartbeat. */
        public long getSessionSeconds() {
            return (at - sessionStart) / 1000;
        }
    }
}

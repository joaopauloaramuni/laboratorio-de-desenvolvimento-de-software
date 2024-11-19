package com.example.WakaTimeAPI.model;

import java.util.List;

public class Data {
    private BestDay best_day;
    private List<Category> categories;
    private String created_at;
    private double daily_average;
    private double daily_average_including_other_language;
    private int days_including_holidays;
    private int days_minus_holidays;
    private List<Dependency> dependencies;
    private List<Editor> editors;
    private String end;
    private int holidays;
    private String human_readable_daily_average;
    private String human_readable_daily_average_including_other_language;
    private String human_readable_range;
    private String human_readable_total;
    private String human_readable_total_including_other_language;
    private String id;
    private boolean is_already_updating;
    private boolean is_cached;
    private boolean is_coding_activity_visible;
    private boolean is_including_today;
    private boolean is_other_usage_visible;
    private boolean is_stuck;
    private boolean is_up_to_date;
    private boolean is_up_to_date_pending_future;
    private List<Language> languages;
    private List<Machine> machines;
    private String modified_at;
    private List<OperatingSystem> operating_systems;
    private int percent_calculated;
    private List<Project> projects;
    private String range; 
    private String start; 
    private String status; 
    private int timeout; 
    private String timezone; 
    private double total_seconds; 
    private double total_seconds_including_other_language; 
    private String user_id; 
    private String username; 
    private boolean writes_only; 

    // Getters and Setters

    public BestDay getBest_day() {
        return best_day;
    }

    public void setBest_day(BestDay best_day) {
        this.best_day = best_day;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public double getDaily_average() {
        return daily_average;
    }

    public void setDaily_average(double daily_average) {
        this.daily_average = daily_average;
    }

    public double getDaily_average_including_other_language() {
        return daily_average_including_other_language;
    }

    public void setDaily_average_including_other_language(double daily_average_including_other_language) {
        this.daily_average_including_other_language = daily_average_including_other_language;
    }

    public int getDays_including_holidays() {
        return days_including_holidays;
    }

    public void setDays_including_holidays(int days_including_holidays) {
        this.days_including_holidays = days_including_holidays;
    }

    public int getDays_minus_holidays() {
        return days_minus_holidays;
    }

    public void setDays_minus_holidays(int days_minus_holidays) {
        this.days_minus_holidays = days_minus_holidays;
    }

    public List<Dependency> getDependencies() {
        return dependencies;
    }

    public void setDependencies(List<Dependency> dependencies) {
        this.dependencies = dependencies;
    }

    public List<Editor> getEditors() {
        return editors;
    }

    public void setEditors(List<Editor> editors) {
        this.editors = editors;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getHolidays() {
        return holidays;
    }

    public void setHolidays(int holidays) {
        this.holidays = holidays;
    }

    public String getHuman_readable_daily_average() {
        return human_readable_daily_average;
    }

    public void setHuman_readable_daily_average(String human_readable_daily_average) {
        this.human_readable_daily_average = human_readable_daily_average;
    }

    public String getHuman_readable_daily_average_including_other_language() {
        return human_readable_daily_average_including_other_language;
    }

    public void setHuman_readable_daily_average_including_other_language(String human_readable_daily_average_including_other_language) {
        this.human_readable_daily_average_including_other_language = human_readable_daily_average_including_other_language;
    }

    public String getHuman_readable_range() {
        return human_readable_range;
    }

    public void setHuman_readable_range(String human_readable_range) {
        this.human_readable_range = human_readable_range;
    }

    public String getHuman_readable_total() {
        return human_readable_total;
    }

    public void setHuman_readable_total(String human_readable_total) {
        this.human_readable_total = human_readable_total;
    }

    public String getHuman_readable_total_including_other_language() {
        return human_readable_total_including_other_language;
    }

    public void setHuman_readable_total_including_other_language(String human_readable_total_including_other_language) {
        this.human_readable_total_including_other_language = human_readable_total_including_other_language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isIs_already_updating() {
        return is_already_updating;
    }

    public void setIs_already_updating(boolean is_already_updating) {
        this.is_already_updating = is_already_updating;
    }

    public boolean isIs_cached() {
        return is_cached;
    }

    public void setIs_cached(boolean is_cached) {
        this.is_cached = is_cached;
    }

    public boolean isIs_coding_activity_visible() {
        return is_coding_activity_visible;
    }

    public void setIs_coding_activity_visible(boolean is_coding_activity_visible) {
        this.is_coding_activity_visible = is_coding_activity_visible;
    }

    public boolean isIs_including_today() {
        return is_including_today;
    }

    public void setIs_including_today(boolean is_including_today) {
        this.is_including_today = is_including_today;
    }

    public boolean isIs_other_usage_visible() {
        return is_other_usage_visible;
    }

    public void setIs_other_usage_visible(boolean is_other_usage_visible) {
        this.is_other_usage_visible = is_other_usage_visible;
    }

    public boolean isIs_stuck() {
        return is_stuck;
    }

    public void setIs_stuck(boolean is_stuck) {
        this.is_stuck = is_stuck;
    }

    public boolean isIs_up_to_date() {
        return is_up_to_date;
    }

    public void setIs_up_to_date(boolean is_up_to_date) {
        this.is_up_to_date = is_up_to_date;
    }

    public boolean isIs_up_to_date_pending_future() {
        return is_up_to_date_pending_future;
    }

    public void setIs_up_to_date_pending_future(boolean is_up_to_date_pending_future) {
        this.is_up_to_date_pending_future = is_up_to_date_pending_future;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Machine> getMachines() {
        return machines;
    }

    public void setMachines(List<Machine> machines) {
        this.machines = machines;
    }

    public String getModified_at() {
        return modified_at;
    }

    public void setModified_at(String modified_at) {
        this.modified_at = modified_at;
    }

    public List<OperatingSystem> getOperating_systems() {
        return operating_systems;
    }

    public void setOperating_systems(List<OperatingSystem> operating_systems) {
        this.operating_systems = operating_systems;
    }

    public int getPercent_calculated() {
        return percent_calculated;
    }

    public void setPercent_calculated(int percent_calculated) {
        this.percent_calculated = percent_calculated;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public double getTotal_seconds() {
        return total_seconds;
    }

    public void setTotal_seconds(double total_seconds) {
        this.total_seconds = total_seconds;
    }

    public double getTotal_seconds_including_other_language() {
        return total_seconds_including_other_language;
    }

    public void setTotal_seconds_including_other_language(double total_seconds_including_other_language) {
        this.total_seconds_including_other_language = total_seconds_including_other_language;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isWrites_only() {
        return writes_only;
    }

    public void setWrites_only(boolean writes_only) {
        this.writes_only = writes_only;
    }
}

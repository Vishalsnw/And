@Database(
    entities = [Goal::class, User::class, Task::class],
    version = 2,
    exportSchema = false,
)
@TypeConverters(Converters::class)
abstract class GoalGuruDatabase : RoomDatabase() {
    abstract fun goalDao(): GoalDao
    abstract fun userDao(): UserDao
    abstract fun taskDao(): TaskDao
}

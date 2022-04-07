using System.Data;
using ConnectionUtils;

namespace CompetitionCSh.utils;

public class DBUtils
{
    private static IDbConnection instance;

    public static IDbConnection getConnection()
    {
        if (instance == null || instance.State == ConnectionState.Closed)
        {
            instance = getNewConnection();
            instance.Open();
        }

        return instance;
    }

    private static IDbConnection getNewConnection()
    {
        return ConnectionFactory.getInstance().createConnection();
    }
}
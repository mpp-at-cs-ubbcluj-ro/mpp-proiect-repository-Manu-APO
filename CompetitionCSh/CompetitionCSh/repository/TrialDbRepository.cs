using CompetitionCSh.domain.dto;
using CompetitionCSh.domain.enums;
using CompetitionCSh.repository.interfaces;
using CompetitionCSh.utils;
using log4net;
using Npgsql;

namespace CompetitionCSh.repository;

public class TrialDbRepository : ITrialRepository
{
    private static readonly ILog log = LogManager.GetLogger("TrialDbRepository");

    public TrialDbRepository()
    {
        log.Info("Creating TrialDbRepository ");
    }

    public IEnumerable<TrialDTO> findAll()
    {
        log.Info("Entering TrialDbRepository findAll");
        var con = DBUtils.getConnection();
        IList<TrialDTO> trials = new List<TrialDTO>();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select * from \"Trial\"";

            using (var dataR = comm.ExecuteReader())
            {
                while (dataR.Read())
                {
                    var id = dataR.GetInt64(0);
                    var competitionId = dataR.GetInt64(1);
                    var maxNumberOfParticipants = dataR.GetInt32(2);
                    TRIAL_TYPE trialType;
                    Enum.TryParse(dataR.GetString(3), out trialType);
                    AGE_CATEGORY ageCategory;
                    Enum.TryParse(dataR.GetString(4), out ageCategory);
                    var startDate = dataR.GetDateTime(5);
                    var endDate = dataR.GetDateTime(6);
                    var trial = new TrialDTO(competitionId, maxNumberOfParticipants, trialType, ageCategory, startDate,
                        endDate);
                    trial.Id = id;
                    trials.Add(trial);
                }
            }
        }

        log.InfoFormat("Exiting findAll with value={0}", string.Join(Environment.NewLine, trials));
        return trials;
    }

    public void save(TrialDTO entity)
    {
        log.InfoFormat("Add trial={0}", entity);
        var con = DBUtils.getConnection();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText =
                "insert into \"Trial\"(\"competitionId\",\"maxNumberOfParticipants\",\"trialType\",\"ageCategory\",\"startDate\",\"endDate\") values (@competitionId, @maxNumberOfParticipants, @trialType, @ageCategory, @startDate, @endDate)";
            comm.Parameters.Add(new NpgsqlParameter("@competitionId", entity.ParentCompetitionId));
            comm.Parameters.Add(new NpgsqlParameter("@maxNumberOfParticipants", entity.MaxNumberOfParticipants));
            comm.Parameters.Add(new NpgsqlParameter("@trialType", entity.TrialType));
            comm.Parameters.Add(new NpgsqlParameter("@ageCategory", entity.AgeCategory));
            comm.Parameters.Add(new NpgsqlParameter("@startDate", entity.startDate));
            comm.Parameters.Add(new NpgsqlParameter("@endDate", entity.endDate));
            var result = comm.ExecuteNonQuery();
            if (result == 0)
                log.Error("Error Add trial!");
            else
                log.Info("Successful adding!");
        }
    }

    public void update(TrialDTO e1, TrialDTO e2)
    {
        throw new NotImplementedException();
    }

    public TrialDTO delete(TrialDTO e)
    {
        throw new NotImplementedException();
    }

    public TrialDTO findOne(long id)
    {
        log.InfoFormat("Entering findOne with value= {0}", id);
        var con = DBUtils.getConnection();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText = "select * from \"Trial\" where id=@id";
            comm.Parameters.Add(new NpgsqlParameter("@id", id));
            using (var dataR = comm.ExecuteReader())
            {
                if (dataR.Read())
                {
                    var competitionId = dataR.GetInt64(1);
                    var maxNumberOfParticipants = dataR.GetInt32(2);
                    TRIAL_TYPE trialType;
                    Enum.TryParse(dataR.GetString(3), out trialType);
                    AGE_CATEGORY ageCategory;
                    Enum.TryParse(dataR.GetString(4), out ageCategory);
                    var startDate = dataR.GetDateTime(5);
                    var endDate = dataR.GetDateTime(6);
                    var trial = new TrialDTO(competitionId, maxNumberOfParticipants, trialType, ageCategory, startDate,
                        endDate);
                    trial.Id = id;
                    log.InfoFormat("Exiting findOne with value {0}", trial);
                    return trial;
                }
            }
        }

        log.InfoFormat("Exiting findOne with value {0}", null);
        return null;
    }
}
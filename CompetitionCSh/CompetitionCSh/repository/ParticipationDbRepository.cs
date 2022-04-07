using CompetitionCSh.domain.dto;
using CompetitionCSh.repository.interfaces;
using CompetitionCSh.utils;
using log4net;
using Npgsql;

namespace CompetitionCSh.repository;

public class ParticipationDbRepository : IParticipationRepository
{
    private static readonly ILog log = LogManager.GetLogger("ParticipationDbRepository");

    public ParticipationDbRepository()
    {
        log.Info("Creating ParticipationDbRepository");
    }

    public IEnumerable<ParticipationDTO> findAll()
    {
        throw new NotImplementedException();
    }

    public void save(ParticipationDTO entity)
    {
        log.InfoFormat("Add participation={0}", entity);
        var con = DBUtils.getConnection();
        using (var comm = con.CreateCommand())
        {
            comm.CommandText =
                "insert into \"Participation\"(\"participantId\",\"trialId\",\"dateOfSubmission\",\"registryId\") values (@idP,@idT,@date,@idR)";
            comm.Parameters.Add(new NpgsqlParameter("@idP", entity.ParticipantId));
            comm.Parameters.Add(new NpgsqlParameter("@idT", entity.TrialId));
            comm.Parameters.Add(new NpgsqlParameter("@date", entity.DateOfSubmission));
            comm.Parameters.Add(new NpgsqlParameter("@idR", entity.RegistryId));

            var result = comm.ExecuteNonQuery();
            if (result == 0)
                log.Error("Error Add participation!");
            else
                log.Info("Successful adding!");
        }
    }

    public void update(ParticipationDTO e1, ParticipationDTO e2)
    {
        throw new NotImplementedException();
    }

    public ParticipationDTO delete(ParticipationDTO e)
    {
        throw new NotImplementedException();
    }

    public ParticipationDTO findOne(long id)
    {
        throw new NotImplementedException();
    }
}